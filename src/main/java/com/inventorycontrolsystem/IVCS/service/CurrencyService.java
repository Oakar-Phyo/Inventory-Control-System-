package com.inventorycontrolsystem.IVCS.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorycontrolsystem.IVCS.model.Currency;
import com.inventorycontrolsystem.IVCS.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	CurrencyRepository currencyRepository;

	public void insertCurrency(Currency currency) {

		currencyRepository.save(currency);

	}

	public void updateCurrency(Currency currency) {
		currencyRepository.save(currency);
	}

	public void deleteCurrency(int id) {
		currencyRepository.deleteById(id);
	}

	public List<Currency> findAllCurrency(){
		List<Currency> list = currencyRepository.findAll();
		return list;
		
	}
	
	public Currency selectByCurrencyCode(String code) {
		return currencyRepository.findByCurrencyCode(code);
	}
	
	public Currency selectOne(int id) {
		return entityManager.find(Currency.class, id);
	}
	
	public List<Currency> getByCurrencyCode(Currency currency){
		List<Currency> list = new ArrayList<Currency>();
		list = currencyRepository.getCurrencyByCode(currency.getName(), currency.getSname());
		return list;
		
	}
}
