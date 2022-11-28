package com.uet.esxi_api.service;

import com.uet.esxi_api.dto.AuthenRequest;
import com.uet.esxi_api.dto.AuthenResponse;

public interface AuthenService {
	AuthenResponse signIn(AuthenRequest request);
}
