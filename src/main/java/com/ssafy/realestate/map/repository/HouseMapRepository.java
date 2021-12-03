package com.ssafy.realestate.map.repository;

import com.ssafy.realestate.map.dto.DongResponseDto;
import com.ssafy.realestate.map.dto.GugunResponseDto;
import com.ssafy.realestate.map.dto.HouseInfoResponseDto;
import com.ssafy.realestate.map.dto.SidoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class HouseMapRepository {

    private final EntityManager entityManager;

    public List<SidoResponseDto> getSido() {
        String sql = "select left(sidoCode,2) sidoCode, sidoName from sidocode order by sidoCode";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql);
        return result.list(query, SidoResponseDto.class);
    }

    public List<GugunResponseDto> getGugunInSido(String sido) {
        String sql = " select left(gugunCode,5) gugunCode, gugunName" +
                " from guguncode where left(gugunCode,2) = ? order by gugunCode";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter(1, sido);
        return result.list(query, GugunResponseDto.class);
    }

    public List<DongResponseDto> getDongInGugun(String gugun) {
        String sql = "    select distinct dongName, dongCode" +
                "        from houseinfo" +
                "        where left(dongCode, 5) = ?" +
                "        order by dongName";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter(1, gugun);
        return result.list(query, DongResponseDto.class);
    }

    public List<HouseInfoResponseDto> getguApt(String gugun) {
        String sql = "select * from" +
                "(select h.aptCode, h.aptName, h.buildyear, h.dongCode, h.dongName, h.jibun, h.lat, h.lng, si.sidoname, gu.gugunname," +
                "(select dealAmount from housedeal" +
                " where aptCode = h.aptCode and no =" +
                "(select max(no) from housedeal where aptCode = h.aptCode)) recentPrice" +
                " from houseinfo h" +
                " join sidocode si" +
                " on left(h.dongcode,2) = left(si.sidocode,2)" +
                "    join guguncode gu" +
                "    on left(h.dongcode,5) = left(gu.guguncode,5)" +
                "    where left(dongCode, 5) = ?" +
                "    order by aptName) aa" +
                "    where aa.recentPrice is not null";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter(1, gugun);
        return result.list(query, HouseInfoResponseDto.class);
    }

    public List<HouseInfoResponseDto> getAptInDong(String dong) {
        String sql = "  select * from" +
                "        (select h.aptCode, h.aptName, h.buildyear, h.dongCode, h.dongName, h.jibun, h.lat, h.lng,si.sidoname,gu.gugunname," +
                "        (select dealAmount from housedeal" +
                "        where aptCode = h.aptCode" +
                "        and no =" +
                "        (select max(no) from housedeal where aptCode = h.aptCode)) recentPrice" +
                "        from houseinfo h" +
                "        join sidocode si" +
                "        on left(h.dongcode,2) = left(si.sidocode,2)" +
                "        join guguncode gu" +
                "        on left(h.dongcode,5) = left(gu.guguncode,5)" +
                "        where dongCode = ?" +
                "        order by aptName) aa" +
                "        where aa.recentPrice is not null";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter(1, dong);
        return result.list(query, HouseInfoResponseDto.class);
    }

    public List<HouseInfoResponseDto> getDongNameSearch(String dongName) {
        String sql = "  select * from " +
                "        (select h.aptCode, h.aptName, h.buildyear, h.dongCode, h.dongName, h.jibun, h.lat, h.lng, si.sidoname,gu.gugunname," +
                "        (select dealAmount from housedeal" +
                "        where aptCode = h.aptCode and no =" +
                "        (select max(no) from housedeal where aptCode = h.aptCode)) recentPrice" +
                "        from houseinfo h" +
                "        join sidocode si" +
                "        on left(h.dongcode,2) = left(si.sidocode,2)" +
                "        join guguncode gu" +
                "        on left(h.dongcode,5) = left(gu.guguncode,5)" +
                "        where dongName LIKE CONCAT('%', ?, '%')" +
                "        order by aptName) aa" +
                "        where aa.recentPrice is not null";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter(1, dongName);
        return result.list(query, HouseInfoResponseDto.class);
    }

    public List<HouseInfoResponseDto> getRecommend(String dongName) {
        String sql = " select *  from" +
                "        (select h.aptCode, h.aptName, h.buildyear, h.dongCode, h.dongName, h.jibun, h.lat, h.lng, si.sidoname," +
                "        gu.gugunname," +
                "        (select dealAmount from housedeal" +
                "        where aptCode = h.aptCode" +
                "        and no =(select max(no) from housedeal where aptCode = h.aptCode)) recentPrice" +
                "        from houseinfo h join sidocode si" +
                "        on left(h.dongcode,2) = left(si.sidocode,2)" +
                "        join guguncode gu" +
                "        on left(h.dongcode,5) = left(gu.guguncode,5)" +
                "        where dongName LIKE CONCAT('%', ?, '%')" +
                "        order by rand() limit 4) aa" +
                "        where aa.recentPrice is not null";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter(1, dongName);
        return result.list(query, HouseInfoResponseDto.class);
    }


    public List<HouseInfoResponseDto> likedAptList(List<String> aptCode) {
        String sql = "      select *from" +
                "        (select h.aptCode, h.aptName, h.buildyear, h.dongCode, h.dongName, h.jibun, h.lat, h.lng, si.sidoname," +
                "        gu.gugunname," +
                "        (select dealAmount from housedeal" +
                "        where aptCode = h.aptCode" +
                "        and no = (select max(no) from housedeal where aptCode = h.aptCode)) recentPrice" +
                "        from houseinfo h" +
                "        join sidocode si" +
                "        on left(h.dongcode,2) = left(si.sidocode,2)" +
                "        join guguncode gu" +
                "        on left(h.dongcode,5) = left(gu.guguncode,5)" +
                "        where aptCode in :list" +
                "        order by aptName) aa" +
                "        where aa.recentPrice is not null";
        JpaResultMapper result = new JpaResultMapper();
        Query query = entityManager.createNativeQuery(sql).setParameter("list", aptCode);
        return result.list(query, HouseInfoResponseDto.class);
    }
}
