$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
$State = (get-Vm -Name $Name).PowerState
If ($State -ne "PoweredOff") {
	Stop-VM -VM $Name -confirm:$false | Out-Null
}
Remove-VM -VM $Name -DeletePermanently -confirm:$false | Out-Null
$existVM = Get-VM -Name $Name -ErrorAction SilentlyContinue
If ($existVM) {
	Write-Host "Fail to delete VM $Name"
} else {
	Write-Host "Delete VM $Name successfully"
}