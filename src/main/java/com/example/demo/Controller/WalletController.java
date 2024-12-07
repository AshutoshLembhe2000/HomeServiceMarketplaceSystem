package com.example.demo.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Wallet.Wallet;
import com.example.demo.Service.WalletService.WalletService;

@RequestMapping("/wallet")
public class WalletController {
	private WalletService walletService;

    @GetMapping("/{userId}/{userType}")
    public Wallet getWallet(@PathVariable int userId, @PathVariable String userType) {
        return walletService.getWalletByUserIdAndType(userId, userType);
    }

    @PostMapping("/addFunds")
    public String addFunds(@RequestParam int userId, @RequestParam String userType, @RequestParam float amount) {
        walletService.updateWalletBalance(userId, amount, userType);
        return "Funds added successfully!";
    }
}