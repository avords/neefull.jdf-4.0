package com.mvc.security.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mvc.security.model.Audit;
@Service("auditManager")
public class AuditManagerImpl implements AuditManager {

	public boolean commitAudit(List<Audit> baseAuditList) {
		return false;
	}
}
