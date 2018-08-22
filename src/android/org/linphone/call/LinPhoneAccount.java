package org.linphone.call;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.util.Log;

import org.linphone.LinphoneManager;
import org.linphone.R;

import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LinPhoneAccount {
    private static TelecomManager telecomManager;
    private PhoneAccountHandle accountHandle;
    private PhoneAccount account;
    private Context mContext;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinPhoneAccount(Context context) {
        mContext=context;
        telecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);

        accountHandle = new PhoneAccountHandle(
                new ComponentName(mContext, LinphoneConnectionService.class),
                mContext.getPackageName());

        String uriAdress = LinphoneManager.getLc().getIdentity();

        account= PhoneAccount.builder(accountHandle,"Linphone")
//                .setAddress(Uri.parse("sip.linphone.org"))
                .setAddress(Uri.fromParts(PhoneAccount.SCHEME_SIP, uriAdress, null))
                .setIcon(Icon.createWithResource(mContext, R.drawable.linphone_logo))
                .setSubscriptionAddress(null)
                .setCapabilities(PhoneAccount.CAPABILITY_CALL_PROVIDER |
                                PhoneAccount.CAPABILITY_VIDEO_CALLING |
                                PhoneAccount.CAPABILITY_CONNECTION_MANAGER
                )
                .setHighlightColor(Color.GREEN)
                .setShortDescription("Enable to allow Linphone integration and set it as default Phone Account in the next panel.")
//                .setSupportedUriSchemes(Arrays.asList("tel, sip"))
                .setSupportedUriSchemes(Arrays.asList(PhoneAccount.SCHEME_SIP, "tel, sip"))
                .build();

                registerPhoneAccount();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void registerPhoneAccount(){
        if (telecomManager != null && account != null){
            telecomManager.registerPhoneAccount(account);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void unregisterPhoneAccount(){
        if (telecomManager != null && accountHandle != null){
            telecomManager.unregisterPhoneAccount(accountHandle);
        }
    }
    public PhoneAccount getAccount(){
        return account;
    }

    public PhoneAccountHandle getAccountHandler(){
        return accountHandle;
    }

}
