import HelperClasses.DigitalSignature;
import HelperClasses.Conversion;
import java.security.KeyPair;

public class Signature extends DigitalSignature {

    public static SignatureKeys keygen() {
        KeyPair keys = generate_keys();
        String sk = Conversion.byteToHex(keys.getPrivate().getEncoded());
        String vk = Conversion.byteToHex(keys.getPublic().getEncoded());
        return new SignatureKeys(sk, vk);
    }

    public static String BoundedMsgSign(String message, String sk) {

        assert message.length() == 64;

        return sign_message(message, sk);
    }

    public static boolean BoundedMsgVerify(String message, String pk, String signature) {

        assert message.length() == 64;

        return verify_signature(message, pk, signature);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/
//  Construct a signature for an arbitrarily long message. For constructing the
// signature, you need to first compress the long input message by applying the appropriate CRF i.e.
// using a CRF instance with outputsize = 64, and then sign the CRF output using the function
// BoundedMsgSign.
    public static String Sign(String m, String sk) {
        CRF CRF_obj = new CRF(64);  // Specify output length here
        String hashedVal = CRF_obj.Fn(m);
        return BoundedMsgSign(hashedVal, sk);
    }
    
    public static boolean Verify(String m, String vk, String sig) {
        CRF CRF_obj = new CRF(64);  // Specify output length here
        String hashedVal = CRF_obj.Fn(m);
        return BoundedMsgVerify(hashedVal, vk, sig);

    }
}
