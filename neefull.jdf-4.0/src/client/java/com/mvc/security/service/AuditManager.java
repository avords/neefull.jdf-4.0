package com.mvc.security.service;

import java.util.List;

import com.mvc.security.model.Audit;

public interface AuditManager {
	boolean commitAudit(List<Audit> baseAuditList);
}
