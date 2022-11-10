$Name = $args[0]
$Storage = $args[1]
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
If ((Get-HardDisk -VM $Name).CapacityGB -lt $Storage) {
	Get-HardDisk -VM $Name | Set-HardDisk -CapacityGB $Storage -Confirm:$false  | Out-Null
	If ((Get-HardDisk -VM $Name).CapacityGB -eq $Storage) {
		write-host "Change successfully"
	} else {
		write-host "Fail to change"
	}
} else {
	write-host "You can only expand the size of storage"
}