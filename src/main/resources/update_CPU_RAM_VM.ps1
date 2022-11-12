$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
$CPU = $args[4]
$RAM = $args[5]
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
Set-VM -VM $Name -NumCpu $CPU -MemoryGB $RAM -Confirm:$false | Out-Null
If (((get-Vm -Name $Name).NumCpu -eq $CPU) -and ((get-Vm -Name $Name).MemoryGB -eq $RAM)) {
	write-host "Change successfully"
} else {
	write-host "Fail to change"
}