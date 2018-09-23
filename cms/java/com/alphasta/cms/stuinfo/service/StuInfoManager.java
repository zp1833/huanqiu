package com.alphasta.cms.stuinfo.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alphasta.common.core.service.BaseManager;

/**
 * 学员信息管理
 * 
 * @author zhaopeng
 * 
 */
public class StuInfoManager extends BaseManager {

	@SuppressWarnings("unchecked")
	public String isContactExist(Integer id, String contact, String contact1) {
		List list = null;
		if (id != null) {
			if(StringUtils.isNotBlank(contact)) {
				list = query("from StuInfo where (contact = ? or contact1 = ?) and id != ?", contact, contact, id);
				if (list.size() > 0)
					return "1";
			}
			
			if(StringUtils.isNotBlank(contact1)) {
				list = query("from StuInfo where (contact = ? or contact1 = ?) and id != ?", contact1, contact1, id);
				if (list.size() > 0)
					return "1";
			}
		} else {
			if(StringUtils.isNotBlank(contact)) {
				list = query("from StuInfo where contact = ? or contact1 = ?", contact, contact);
				if (list.size() > 0)
					return "1";
			}
			
			if(StringUtils.isNotBlank(contact1)) {
				list = query("from StuInfo where contact = ? or contact1 = ?", contact1, contact1);
				if (list.size() > 0)
					return "1";
			}
		}

		if (list.size() > 0)
			return "1";
		
		
		
		return "0";
	}
}
