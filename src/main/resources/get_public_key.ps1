$pubkey = 
$vmName = 
Invoke-VMScript -VM $vmName -ScriptText "mkdir ~/.ssh;touch ~/.ssh/authorized_keys; echo $pubkey >> ~/.ssh/authorized_keys; history -c" -GuestUser uuser -GuestPassword "123"