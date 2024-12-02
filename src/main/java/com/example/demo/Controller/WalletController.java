package com.example.demo.Controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Wallet.Wallet;
import com.example.demo.Service.WalletService.WalletService;

@RequestMapping("/wallet")
public class WalletController {
	private WalletService walletService;

    @GetMapping("/{userId}")
    public Wallet getWallet(@PathVariable int userId) {
        return walletService.getWalletByUserId(userId);
    }

    @PostMapping("/addFunds")
    public String addFunds(@RequestParam int userId, @RequestParam float amount) {
        walletService.updateWalletBalance(userId, amount);
        return "Funds added successfully!";
    }
}