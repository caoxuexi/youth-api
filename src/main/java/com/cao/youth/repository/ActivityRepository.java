
package com.cao.youth.repository;

import com.cao.youth.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Activity findByName(String name);

    Optional<Activity> findByCouponListId(Long couponId);
}
