package top.yxy.txdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.yxy.txdemo.entity.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
