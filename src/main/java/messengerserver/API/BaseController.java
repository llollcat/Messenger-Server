/*
package messengerserver.API;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class BaseController {
    private static final String SUCCESS_STATUS = "success";



    @GetMapping
    public BaseResponse showStatus() {
        return new BaseResponse(SUCCESS_STATUS, 1);
    }
    @PostMapping
    public BaseResponse showStatus2() {
        return new BaseResponse(SUCCESS_STATUS, -11);
    }


    */
/*@PostMapping("/pay")
    public BaseResponse pay(@RequestParam(value = "key") String key, @RequestBody PaymentRequest request) {
        final BaseResponse response;
        if (sharedKey.equalsIgnoreCase(key)) {
            int userId = request.getUserId();
            String itemId = request.getItemId();
            double discount = request.getDiscount();
            response = new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
        } else {
            response = new BaseResponse(ERROR_STATUS, AUTH_FAILURE);
        }
        return response;
    }*//*

}*/
