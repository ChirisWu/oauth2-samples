package com.tencent.chiris.projectrank.mapper;

import com.tencent.chiris.projectrank.model.ProjectStarRank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProjectStarRankMapper {

    int insertRanks(@Param("records") List<ProjectStarRank> records);

    int deleteRanks(@Param("taskDate") Date taskDate);

    Date getLastTaskDate(@Param("type") Integer type);

    ProjectStarRank getLastStarRank(@Param("type") Integer type, @Param("targetId") Integer targetId);

    int getTotalFast(@Param("taskDate") Date taskDate, @Param("type") Integer type);

    // 排名统计
    List<ProjectStarRank> processProjectRecentRank(@Param("ctype") int categoryType, @Param("visibilityLevels") List<Integer> visibilityLevels, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("taskDate") Date taskDate);

    List<ProjectStarRank> processProjectInnerSourceRecentRank(@Param("ctype") int categoryType, @Param("visibilityLevels") List<Integer> visibilityLevels, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("taskDate") Date taskDate);

    List<ProjectStarRank> processGroupRank(@Param("ctype") int categoryType, @Param("projectRankType") int projectRankType, @Param("taskDate") Date taskDate);

    List<ProjectStarRank> processTagProjectRank(@Param("ctype") int categoryType, @Param("projectRankType") int projectRankType, @Param("taskDate") Date taskDate);

    //
    List<ProjectStarRank> processGroupRankWithoutRank(@Param("ctype") int categoryType, @Param("projectRankType") int projectRankType, @Param("taskDate") Date taskDate);

    int updateStart(@Param("id") Integer id, @Param("star") Integer star, @Param("taskDate") Date taskDate);

}