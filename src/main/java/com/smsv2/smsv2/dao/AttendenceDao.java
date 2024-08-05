package com.smsv2.smsv2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Sub;





public interface AttendenceDao extends JpaRepository<Attendence, Integer> {
List<Attendence> findBySubid_Id(int subid);
Optional<Attendence> findByName(String name);
}
