package com.pandatronik.backend.service.user.account;


import com.google.common.base.Preconditions;
import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.repositories.user.account.PlanRepository;
import com.pandatronik.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PlanService {

	private static final Logger LOG = LoggerFactory.getLogger(PlanService.class);

	@Autowired
	private PlanRepository planRepository;

	public Plan findPlanById(int planId) {
		Preconditions.checkNotNull(planId, "planId must not be null");
		return planRepository.findById(planId).get();
	}

	@Transactional
	public Plan createPlan(int planId) {
		Preconditions.checkNotNull(planId, "planId must not be null");
		Plan plan = null;
		if (planId == 1) {
			plan = planRepository.save(new Plan(PlansEnum.BASIC)); // BASIC
		} else if (planId == 2) {
			plan = planRepository.save(new Plan(PlansEnum.PRO));
		} else {
			throw new IllegalArgumentException("Plan id " + planId + " not recognised.");
		}
		return plan;
	}

}
