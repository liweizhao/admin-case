package org.ricky.admin.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.ricky.admin.ddo.UserDo;

public interface UserCustomMapper {

	List<UserDo> selectUserList(@Param("limit")Integer limit,
			@Param("offset")Integer offset);
}
