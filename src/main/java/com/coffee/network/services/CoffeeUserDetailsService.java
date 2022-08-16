package com.coffee.network.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coffee.network.repositories.UsersRepository;

@Service
public class CoffeeUserDetailsService implements UserDetailsService {

  private UsersRepository usersRepository;

  public CoffeeUserDetailsService(
    @Autowired UsersRepository usersRepository
  ) {
    this.usersRepository = usersRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usersRepository.findByUsername(username)
      .map((user) -> new CoffeeUserDetails(user))
      .orElseThrow(() ->
        new UsernameNotFoundException("User " + username + " was not found")
      );
  }

}
