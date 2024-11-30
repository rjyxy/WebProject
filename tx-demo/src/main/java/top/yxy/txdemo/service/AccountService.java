package top.yxy.txdemo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.yxy.txdemo.entity.Account;
import top.yxy.txdemo.mapper.AccountMapper;

@Service
@AllArgsConstructor
public class AccountService extends ServiceImpl<AccountMapper,Account> {
    private AccountMapper accountMapper;

//    @Transactional(rollbackFor = Exception.class)
    public void transferMoney(Long fromId, Long toId, Double amount){
        //查询用户
        Account fromAccount = accountMapper.selectById(fromId);
        Account toAccount = accountMapper.selectById(toId);

//        //检查余额是否足够
//        if (fromAccount.getBalance() < amount) {
//            throw new RuntimeException("余额不足");
//        }

        //扣减
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountMapper.updateById(fromAccount);

        //增加
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountMapper.updateById(toAccount);

        //模拟单笔转账金额限制
        if (amount > 2000) {
            throw new RuntimeException("转账金额过大，触发事务回滚！");
        }
    }

}
