package com.ifs.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ifs.dto.RequestDto;
import com.ifs.dto.ResponseDto;
import com.ifs.entity.User;

@Component
public class UserMapper {
	private static ModelMapper modelMapper;

	public UserMapper(ModelMapper modelMapper) {
		UserMapper.modelMapper = modelMapper;
    }
	
	public static User toUser(RequestDto dto) {
		return modelMapper.map(dto, User.class);
	}
	
	public static ResponseDto toDto(User user) {
		return modelMapper.map(user, ResponseDto.class);
	}
}
