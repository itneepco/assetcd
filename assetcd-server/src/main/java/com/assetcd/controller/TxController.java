package com.assetcd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.assetcd.data.model.MappingCode;
import com.assetcd.data.model.MappingNewCode;
import com.assetcd.data.model.MappingRejectedCode;
import com.assetcd.data.repository.UserRepository;
import com.assetcd.exception.AlreadyProcessedException;
import com.assetcd.exception.MappingNotFoundException;
import com.assetcd.service.CustomUserDetailsService;
import com.assetcd.service.TxService;
import com.assetcd.vo.CreateCommand;
import com.assetcd.vo.RejectCommand;
import com.assetcd.vo.UpdateCommand;
import com.assetcd.vo.UpdateRejectedCommand;

//@Controller
//@BasePathAwareController
@RestController
@RequestMapping("/tx")
public class TxController {
	
	@Autowired
	TxService txService;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(path = "/rmc", method = RequestMethod.POST)
	@ResponseBody
    public MappingRejectedCode rejectMappingCode(@RequestBody RejectCommand command, Principal principal) throws AlreadyProcessedException {
		
		MappingRejectedCode mrc = command.getMappingRejectedCode();
		Integer mcId = command.getMappingCodeId();
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		//String username = principal.getName();
		//User user = userRepository.findByUserCode(username);
		//return txService.reject(mrc, mcId, user.getId());
		return txService.reject(mrc, mcId, userCode);

	}
	
	@RequestMapping(path = "/cmnc", method = RequestMethod.POST)
	@ResponseBody
    public MappingNewCode createMappingNewCode(@RequestBody CreateCommand command, Principal principal) throws AlreadyProcessedException {
		
		MappingNewCode mnc = command.getMappingNewCode();
		Integer mcId = command.getMappingCodeId();
		//String preDesc = command.getPreDesc();
		//String postDesc = command.getPostDesc();
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		//String username = principal.getName();
		//User user = userRepository.findByUserCode(username);
		//return txService.create(mnc, mcId, user.getId());
		
		return txService.create(mnc, mcId, userCode);

	}
	
	@RequestMapping(path = "/umnc", method = RequestMethod.POST)
	@ResponseBody
    public MappingNewCode updateMappingNewCode(@RequestBody UpdateCommand command, Principal principal) throws MappingNotFoundException {
		
		MappingNewCode mnc = command.getMappingNewCode();
		Integer mncId = command.getMappingNewCodeId();
		//String preDesc = command.getPreDesc();
		//String postDesc = command.getPostDesc();
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		//String username = principal.getName();
		//User user = userRepository.findByUserCode(username);
		//return txService.create(mnc, mcId, user.getId());
		
		return txService.update(mnc, mncId, userCode);

	}
	
	@RequestMapping(path = "/umrc", method = RequestMethod.POST)
	@ResponseBody
    public MappingCode updateMappingRejectedCode(@RequestBody UpdateRejectedCommand command, Principal principal) throws MappingNotFoundException {
		
		MappingCode mc = command.getMappingCode();
		Integer mrcId = command.getMappingRejectedCodeId();
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		return txService.updateRejected(mc, mrcId, userCode);

	}

	@RequestMapping(path = "/rmrc", method = RequestMethod.POST)
	@ResponseBody
    public MappingCode resendMappingRejectedCode(@RequestBody MappingRejectedCode mappingRejectedCode, Principal principal) throws MappingNotFoundException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		return txService.resendRejected(mappingRejectedCode, userCode);

	}

	
		
}
