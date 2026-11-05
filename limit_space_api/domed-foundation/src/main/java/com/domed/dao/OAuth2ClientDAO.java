package com.domed.dao;

import com.domed.core.dao.GenericDAO;
import com.domed.domain.OAuth2Client;
import org.springframework.stereotype.Repository;

@Repository("oauth2ClientDAO")
public class OAuth2ClientDAO extends GenericDAO<OAuth2Client> {
	
}
