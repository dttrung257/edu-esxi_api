$Name = $args[0]
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
Stop-VM -VM $Name -confirm:$false | Out-Null
Remove-VM -VM $Name -DeletePermanently -confirm:$false | Out-Null
$existVM = Get-VM -Name $Name -ErrorAction SilentlyContinue
If ($existVM) {
	Write-Host "Delete successfully VM $Name"
} else {
	Write-Host "Fail to delete VM $Name"
}