const fs=require('fs'),path=require('path'),{spawnSync}=require('child_process');
const root = process.env.VEHICLE_PROJECT_ROOT || path.resolve(__dirname,'../..');
const jar=path.join(root,'local-development/vehicle-test-server/target/vehicle-local-test-server-0.0.1-LOCAL-SNAPSHOT.jar');
const java=path.join(process.env.JAVA_HOME||'C:/Program Files/Java/jdk1.8.0_91','bin/java.exe');
const base=['--vehicle.local-test.enabled=true','--vehicle.local-test.key=negative-startup-test-key-01234567890123456789','--spring.datasource.password=negative-startup-db-password-0123456789'];
const cases=[
 ['disabled-default',[]],
 ['production-profile',[...base,'--spring.profiles.active=prod']],
 ['mixed-production-profile',[...base,'--spring.profiles.active=vehicle-local-test,prod']],
 ['oracle-url',[...base,'--spring.profiles.active=vehicle-local-test','--spring.datasource.url=jdbc:oracle:thin:@127.0.0.1:1521:orcl']],
 ['indirect-jpa-url',[...base,'--spring.profiles.active=vehicle-local-test','--spring.jpa.properties.hibernate.connection.url=jdbc:oracle:thin:@127.0.0.1:1521:orcl']]
];
const results=cases.map(([name,args])=>{
 const result=spawnSync(java,['-Dfile.encoding=UTF-8','-jar',jar,...args],{encoding:'utf8',timeout:30000,windowsHide:true});
 const output=(result.stdout||'')+(result.stderr||'');
 if(result.status===0 || !output.includes('LOCAL TEST GUARD:') || output.includes('Tomcat started on port'))throw Error('Unsafe/unknown startup outcome for '+name+': '+output.slice(-1000));
 console.log('PASS startup refused: '+name);
 return {name,exitCode:result.status,guardRejected:true,serverStarted:false};
});
fs.writeFileSync(path.join(root,'tests/local-development/startup-refusal-results.json'),JSON.stringify(results,null,2)+'\n');
console.log('PASS '+results.length+' actual startup refusal checks; no Oracle connection attempted.');
