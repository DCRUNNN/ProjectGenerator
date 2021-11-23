/**
 * url地址
 * @param url
 * @returns {boolean}
 */
export function isValidateURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return reg.test(url)
}

/**
 * 小写字母
 * @param str
 * @returns {boolean}
 */
export function isValidateLowerCase(str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * 大写字母
 * @param str
 * @returns {boolean}
 */
export function isValidateUpperCase(str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * 大小写字母
 * @param str
 * @returns {boolean}
 */
export function isValidateAlphabets(str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

/**
 * 邮箱
 * @param email
 * @returns {boolean}
 */
export function isValidateEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return reg.test(email)
}

/**
 * 角色名
 * @param roleName
 * @returns {boolean}
 */
export function isValidateRoleName(roleName) {
  const reg = /^ROLE_[A-Z]+$/
  return reg.test(roleName)
}

/**
 *  验证电话号码
 * @param phoneNumber
 * @returns {boolean}
 */
export function isValidatePhoneNumber(phoneNumber) {
  // const reg = /^1(3|4|5|7|8)\d{9}$/
  // return reg.test(phoneNumber)
  return true;
}

/**
 *  验证银行账号
 * @param bankAccount
 * @returns {boolean}
 */
export function isValidateBankAccount(bankAccount) {
  // const reg = /^1(3|4|5|7|8)\d{9}$/
  // return reg.test(phoneNumber)
  return true
}

/**
 *  验证身份证号
 * @param idCard
 * @returns {boolean}
 */
export function isValidateIdCard(idCard) {
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
  return reg.test(idCard);
}
