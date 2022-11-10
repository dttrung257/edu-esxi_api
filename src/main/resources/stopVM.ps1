$Name = $args[0]
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
Stop-VM -VM $Name -confirm:$false