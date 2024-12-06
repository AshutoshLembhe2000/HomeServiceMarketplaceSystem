package com.example.demo.Service.WalletService;

import com.example.demo.DAORepo.WalletRepository;
import com.example.demo.Model.Wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet getWalletByUserIdAndType(int userId, String userType) {
        Wallet wallet = walletRepository.findByUserIdAndType(userId, userType);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user ID: " + userId + " and type: " + userType);
        }
        return wallet;
    }

    public void updateWalletBalance(int userId, float amount, String userType) {
        Wallet wallet = walletRepository.findByUserIdAndType(userId, userType);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user ID: " + userId + " and type: " + userType);
        }
        float newBalance = wallet.getBalance() + amount; // Add amount to the existing balance
        walletRepository.updateWalletBalance(userId, userType, newBalance);
    }

    public boolean deductFromWallet(int userId, float amount, String userType) {
        Wallet wallet = walletRepository.findByUserIdAndType(userId, userType);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found for user ID: " + userId);
        }
        if (wallet.getBalance() < amount) {
            return false; // Insufficient balance
        }
        float newBalance = wallet.getBalance() - amount; // Deduct amount from the existing balance
        walletRepository.updateWalletBalance(userId, userType, newBalance);
        return true;
    }
    
    
    // Get wallet balance by user ID
    public float getWalletBalanceByUserId(int userId, String userType) {
        Wallet wallet = walletRepository.findByUserIdAndType(userId, userType);
        if (wallet != null) {
            return wallet.getBalance();
        }
        return 0.0f; // Return 0 if no wallet is found for the user
    }
    
  
}
