package top.yxy.txdemo.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yxy.txdemo.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long from, @RequestParam Long to,@RequestParam Double amount) {
        try {
            accountService.transferMoney(from, to, amount);
            return "转账成功";
        } catch (Exception e) {
            return "转账失败";
        }
    }
}
