package com.example.demo.Service.OTP;

import com.example.demo.DAORepo.OTPRepository;
import com.example.demo.Model.OTPService.OTPServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    @Autowired
    private final OTPRepository otpRepository;

    public OTPService(OTPRepository otpRepository) {
        this.otpRepository = otpRepository;
    }


    public void updateBookingStatusToCompleted(String bookingId) {
        // Use the repository to update the booking status
        otpRepository.updateBookingStatusTOComplete(bookingId, "Completed");
    }

    public boolean verifyOTP(String otpCode, String bookingId) {
        OTPServiceModel otpService = otpRepository.findOTPByCode(otpCode, bookingId);
        return otpService != null;
    }


}
