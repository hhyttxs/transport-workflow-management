//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class test {
    @Value("${rsa.publicKey}")
    public String publicKey;

    public test() {
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(final String publicKey) {
        this.publicKey = publicKey;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof test)) {
            return false;
        } else {
            test other = (test)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$publicKey = this.getPublicKey();
                Object other$publicKey = other.getPublicKey();
                if (this$publicKey == null) {
                    if (other$publicKey != null) {
                        return false;
                    }
                } else if (!this$publicKey.equals(other$publicKey)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof test;
    }

    public int hashCode() {
        int result = 1;
        Object $publicKey = this.getPublicKey();
        result = result * 59 + ($publicKey == null ? 43 : $publicKey.hashCode());
        return result;
    }

    public String toString() {
        return "test(publicKey=" + this.getPublicKey() + ")";
    }
}
