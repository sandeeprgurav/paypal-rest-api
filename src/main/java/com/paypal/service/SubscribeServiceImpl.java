package com.paypal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.entity.PricingDetails;
import com.paypal.entity.Subscribe;
import com.paypal.repository.SubscribeRepository;
import com.paypal.utils.DateUtils;

@Service
public class SubscribeServiceImpl implements ISubscribeService {

	@Autowired
	SubscribeRepository subscribeRepository;

	@Override
	public List<Subscribe> findSubscribes(String processDate) {
		try {
			return subscribeRepository.findByOrderDate(DateUtils.getStringToDate(processDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Double pricing(String customerId) {
		List<PricingDetails> pricingDetails = subscribeRepository.getPricingDetails(customerId);
		double interestRate = 0.03;
		Double remainigPayment = pricingDetails.stream().map(x -> x.getRemainingAmount()).reduce(0.0, Double::sum);
		Double totalBillingAmount = pricingDetails.stream().map(x -> x.getAmount()).reduce(0.0, Double::sum);
		double subscriptionFee = (totalBillingAmount - remainigPayment) * interestRate;
		return subscriptionFee;
	}

}
