$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
(get-Vm -Name $Name).PowerState
write-host "|"
$State = (get-Vm -Name $Name).PowerState
If ($State -eq "PoweredOn") {
	(Get-VM -Name $Name).Guest.IPAddress[0]
	write-host "|"
} esle {
	write-host ""
	write-host "|"
}
(get-Vm -Name $Name).NumCpu
write-host "|"
(get-Vm -Name $Name).MemoryGB
write-host "|"
(Get-HardDisk -VM $Name).CapacityGB