// Bundled ONLY by the opt-in localhost development configuration. No production token storage.
import {actorChoices, getLocalActor, assertLocalBrowser} from './actor';
export function getUser() { assertLocalBrowser(); const role=getLocalActor(); return {gid: role === 'applicant' ? 'LVTEST_APPLICANT' : role === 'preaccept' ? 'LVTEST_PRE_REVIEWER' : role === 'accept' ? 'LVTEST_ACCEPT_REVIEWER' : 'LVTEST_LOCAL_ADMIN', code: role, name: actorChoices.find(x=>x.value===role).label, yhcdGid:'LOCAL_FIXTURE_ONLY', userType:'2', xzjgCode:'TEST', xzqhCode:'210202'}; }
export function getToken() { return null; }
export function getRefreshToken() { return null; }
export function removeToken() {}
export function removeRefreshToken() {}
export function setToken() { throw new Error('当前环境不签发登录令牌'); }
export function setRefreshToken() { throw new Error('当前环境不签发登录令牌'); }
export function setUser() { throw new Error('Local identities are pinned server-side'); }
