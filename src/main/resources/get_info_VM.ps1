$Name = $args[0]
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
(get-Vm -Name $Name).PowerState
write-host "|"
$State = (get-Vm -Name $Name).PowerState
If ($State -eq "PoweredOn") {
	(Get-VM -Name $Name).Guest.IPAddress[0]
	write-host "|"
}
(get-Vm -Name $Name).NumCpu
write-host "|"
(get-Vm -Name $Name).MemoryGB
write-host "|"
(Get-HardDisk -VM $Name).CapacityGB