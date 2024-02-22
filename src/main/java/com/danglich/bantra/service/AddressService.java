package com.danglich.bantra.service;

import com.danglich.bantra.dto.AddressRequest;
import com.danglich.bantra.model.Address;

public interface AddressService {
	
	Address create(AddressRequest request);
	
	Address update(Address request);
	
	void delete(int theId);

}
