package com.pethotel.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pethotel.Models.HotelStaff;
import com.pethotel.Repositories.HotelStaffRepository;

@Service
public class HotelStaffUserDetailsService implements UserDetailsService {

	@Autowired
	private HotelStaffRepository hotelStaffRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<HotelStaff> staff = hotelStaffRepository.findByUsername(username);
		if (staff.isEmpty()) {
			throw new UsernameNotFoundException("Could not find user with username: " + username);
		}
		HotelStaff currStaff = staff.get(); 				
		return new User(
				currStaff.getUsername(),
				currStaff.getPassword(),
	              AuthorityUtils.createAuthorityList("STAFF")
	      );                
	}
}
