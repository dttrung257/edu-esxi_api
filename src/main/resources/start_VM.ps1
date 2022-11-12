$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
$State = (get-Vm -Name $Name).PowerState
If ($State -ne "PoweredOn") {
	Start-VM -VM $Name -confirm:$false
}
