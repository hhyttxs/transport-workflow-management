/* 产引入jsencrypt实现数据RSA加密 */
import JSEncrypt from 'jsencrypt' // 处理长文本数据时报错 jsencrypt.js Message too long for RSA
/* 产引入encryptlong实现数据RSA加密 */
import Encrypt from 'encryptlong' // encryptlong是基于jsencrypt扩展的长文本分段加解密功能。

// 密钥对生成 http://web.chacuo.net/netrsakeypair

// 公钥key
var publicKey = "-----BEGIN PUBLIC KEY-----" +
  "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnz9+TfvjEzWKglPnogRWOsQ0U" +
  "UxT/qmWiUtUJgKzHtCMJlR1/XJsEd6XQB7pTRNFKQ1G3xT1FXkVKAyzxfBGCHMkF" +
  "1+z3pEhsx9ye0kz+HrXFtBlrUUT9iBtjpGdYcSftKgtvNgsWyC3yZqEOtdQu4v7O" +
  "hk7vVXDYMX5RMuhTvwIDAQAB" +
  "-----END PUBLIC KEY-----"

// 私钥key
const privateKey = "" /* Private key omitted from local demo distribution */;
export const rsaUtil = {
  /* JSEncrypt加密 */
  rsaPublicData(data) {
    var jsencrypt = new JSEncrypt()
    jsencrypt.setPublicKey(publicKey)
    // 如果是对象/数组的话，需要先JSON.stringify转换成字符串
    var result = jsencrypt.encrypt(data)
    return result
  },
  /* JSEncrypt解密 */
  rsaPrivateData(data) {
    var jsencrypt = new JSEncrypt()
    jsencrypt.setPrivateKey(privateKey)
    // 如果是对象/数组的话，需要先JSON.stringify转换成字符串
    var result = jsencrypt.encrypt(data)
    return result
  },
  /* 加密 */
  encrypt(data) {
    const PUBLIC_KEY = publicKey
    var encryptor = new Encrypt()
    encryptor.setPublicKey(PUBLIC_KEY)
    // 如果是对象/数组的话，需要先JSON.stringify转换成字符串
    const result = encryptor.encryptLong(data)
    return result
  },
  /* 解密 - PRIVATE_KEY - 验证 */
  decrypt(data) {
    const PRIVATE_KEY = privateKey
    var encryptor = new Encrypt()
    encryptor.setPrivateKey(PRIVATE_KEY)
    // 如果是对象/数组的话，需要先JSON.stringify转换成字符串
    var result = encryptor.decryptLong(data)
    return result
  }
}
