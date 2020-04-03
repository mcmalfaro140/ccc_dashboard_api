package com.ccc.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.MetricAlarm;

@Repository
public interface MetricAlarmRepository extends JpaRepository<MetricAlarm, Long> {
	@Query(
			value="SELECT * FROM MetricAlarms WHERE MetricAlarmId in (SELECT MetricAlarmId FROM XRefUserMetricAlarm WHERE UserId = :userid);",
			nativeQuery=true
		)
		public List<MetricAlarm> findAlarmsById(@Param("userid") String userid);
	
	@Modifying
	@Query(
			value="DELETE FROM MetricAlarms WHERE MetricAlarmId  = :metricalarmid",
			nativeQuery=true
		)
		public void deleteAlarmsById(@Param("metricalarmid") String metricalarmid);
	
	@Modifying
	@Query(
			value="DELETE FROM XRefUserMetricAlarm WHERE MetricAlarmId  = :metricalarmid",
			nativeQuery=true
		)
		public void cleanXRefUserMetricAlarmById(@Param("metricalarmid") String metricalarmid);
	@Modifying
	@Query(
			value="INSERT INTO MetricAlarms (AlarmArn) VALUES (:metricalarm)",
			nativeQuery=true
		)
		public void insertNewMetricAlarm(@Param("metricalarm") String metricalarm);
	@Modifying
	@Query(
			value="INSERT INTO XRefUserMetricAlarm (UserId,MetricAlarmId) VALUES (:userid,:metricalarmid)",
			nativeQuery=true
		)
		public void insertNewXRefUserMetricAlarm(@Param("userid") String userid,@Param("metricalarmid") String metricalarmid);
	@Modifying
	@Query(
			value="DELETE FROM XRefUserMetricAlarm WHERE UserId =:userid AND MetricAlarmId =:metricalarmid",
			nativeQuery=true
		)
		public void deleteXRefUserMetricAlarm(@Param("userid") String userid,@Param("metricalarmid") String metricalarmid);
	@Query(
			value="SELECT * FROM MetricAlarms WHERE AlarmArn = :metricalarm",
			nativeQuery=true
		)
		public MetricAlarm getMetricAlarm(@Param("metricalarm") String metricalarm);
}
