$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
$Storage = $args[4]
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
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