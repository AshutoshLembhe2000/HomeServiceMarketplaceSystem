	package com.example.demo.Service.WalletService;

import com.example.demo.DAORepo.WalletRepository;
import com.example.demo.Model.Wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet getWalletByUserId(int userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user ID: " + userId);
        }
        return wallet;
    }

    public void updateWalletBalance(int userId, float amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user ID: " + userId);
        }
        float newBalance = wallet.getBalance() + amount; // Add amount to the existing balance
        walletRepository.updateWalletBalance(userId, newBalance);
    }

    public boolean deductFromWallet(int userId, float amount) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user ID: " + userId);
        }
        if (wallet.getBalance() < amount) {
            return false; // Insufficient balance
        }
        float newBalance = wallet.getBalance() - amount; // Deduct amount from the existing balance
        walletRepository.updateWalletBalance(userId, newBalance);
        return true;
    }
    
    
    // Get wallet balance by user ID
    public float getWalletBalanceByUserId(int userId) {
        Wallet wallet = walletRepository.findByUserId(userId);
        if (wallet != null) {
            return wallet.getBalance();
        }
        return 0.0f; // Return 0 if no wallet is found for the user
    }
    
  
}
