$Name = $args[0]
$CPU = $args[1]
$RAM = $args[2]
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
Set-VM -VM $Name -NumCpu $CPU -MemoryGB $RAM -Confirm:$false | Out-Null
If (((get-Vm -Name $Name).NumCpu -eq $CPU) -and ((get-Vm -Name $Name).MemoryGB -eq $RAM)) {
	write-host "Change successfully"
} else {
	write-host "Fail to change"
}