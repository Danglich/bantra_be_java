package com.danglich.bantra.service;

import java.util.List;

import com.danglich.bantra.dto.AddressRequest;
import com.danglich.bantra.model.UserAddress;

public interface UserAddressService {
	
	UserAddress create(AddressRequest request);
	
	UserAddress update(int theId ,AddressRequest request);
	
	List<UserAddress> getByUserId(int userId);
	
	UserAddress setDefault(int theId);
	
	void delete(int theId);

}
