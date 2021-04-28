package com.tencent.chiris.projectrank.service;

import cn.hutool.core.collection.CollUtil;
import com.tencent.chiris.projectrank.mapper.ProjectStarRankMapper;
import com.tencent.chiris.projectrank.model.ProjectStarRank;
import com.tencent.chiris.projectrank.util.SortUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.stream.events.StartDocument;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ProcessGroupRankInMemory {

    @Resource
    private ProjectStarRankMapper projectStarRankMapper;

    private static final int THREAD_SLEEP = 66;
    private static final int THREAD_THRESHOLD = 200000;
    private static final int RANK_QUERY_LIMIT = 50000;


    public List<ProjectStarRank> processGroupStarRank() throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date today = fmt.parse("2021-04-09");
        long start = System.currentTimeMillis();
        List<ProjectStarRank> ranksWithoutRank = projectStarRankMapper.processGroupRankWithoutRank(ProjectStarRank.TYPE_GROUP_INTERNAL_60, ProjectStarRank.TYPE_PROJ_INTERNAL_60, today);
        Comparator<ProjectStarRank> comparator = Comparator.comparing(ProjectStarRank::getStar);
        SortUtil.shellSort(ranksWithoutRank, comparator);
        setRanks(ranksWithoutRank);
        log.info("sort in memory cost {}", System.currentTimeMillis() - start);
        return ranksWithoutRank;

    }
    private static void setRanks(List<ProjectStarRank> ranks){
        long start = System.currentTimeMillis();
        int seed = 0;
        ranks.get(0).setRank(1);
        for (int i = 1; i < ranks.size(); i++){
            if (ranks.get(i - 1).getStar().equals(ranks.get(i).getStar())){
                seed ++;
            }
            ranks.get(i).setRank(i + 1 - seed);
        }
        System.out.println("set rank completed cost " + (System.currentTimeMillis() - start));
    }


    @Transactional
    public void deleteRanksYearAgo() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        Date lastYear = cal.getTime();
        log.info("start delete ranks one year ago" );
        long begin = System.currentTimeMillis();
        int count = 0;
        for (;;){
            List<Integer> ranks = projectStarRankMapper.getRankIdsBeforeDate(lastYear);
            if (CollUtil.isEmpty(ranks)){
                break;
            }
            batchDeleteRanks(ranks);
            count += ranks.size();
            log.info("deleting ranks and has deleted {} ranks, cost: {}s", count, (System.currentTimeMillis() - begin) / 1000);
        }
        long end = System.currentTimeMillis();
        log.info("ranks one year ago delete completed, delete {} ranks, cost {}min, {} ms", count, (((end - begin) / 1000) / 60), end );

    }

    private static  final ThreadPoolExecutor deleteRanksExecutor = new ThreadPoolExecutor(100, 100, 0L,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            getThreadFactory("delete-Thread-pool"));


    private void batchDeleteRanks(List<Integer> ids){
        int size = ids.size();
        if (ids.size() > 0){
            for (int i = size; i > 0; i -= 100){
                int start = i - 100;
                projectStarRankMapper.deleteRankByIds(ids.subList(Math.max(start, 0), i));
                try {
                    Thread.sleep(THREAD_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void MultiThreadDeleteRanks(List<Integer> ids){
        if (CollUtil.isEmpty(ids)){
            return;
        }
        int size = ids.size();
        for (int i = size; i > 0; i -= THREAD_THRESHOLD){
            int start = i - THREAD_THRESHOLD;
            int finalI = i;
            deleteRanksExecutor.submit(() -> {
                batchDeleteRanks(ids.subList(Math.max(start, 0), finalI));
            });
        }
    }

    private static  ThreadFactory getThreadFactory(String poolName){
        return new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                String threadName = poolName + "—thread—" + poolNumber.getAndIncrement();
                Thread t = new Thread(r, threadName);
                t.setDaemon(true);
                return t;
            }
        };
    }


}
