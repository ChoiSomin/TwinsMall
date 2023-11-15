package com.mall.twins.twinsmall.service.validator;

import com.mall.twins.twinsmall.dto.MemberJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class CheckPasswordEqualValidator extends AbstractValidator<MemberJoinDTO> {
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoinDTO.class.equals(clazz);
    }

    @Override
    protected void doValidate(MemberJoinDTO dto, Errors errors) {
        if (!dto.getMpw().equals(dto.getMpw_confirm())) {
            errors.rejectValue("mpw_confirm", "비밀번호 일치 오류", "비밀번호가 일치하지 않습니다.");
        }
    }
}
