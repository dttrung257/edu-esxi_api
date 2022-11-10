
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials
$myTargetVMhost = Get-VMHost -Name 192.168.0.112 
<# create VM #>
$existVM = Get-VM -Name VM1 -ErrorAction SilentlyContinue
If (!$existVM) {
	New-VM -Name VM1 -ResourcePool $myTargetVMhost -Datastore datastore1 -NumCpu 2 -MemoryGB 2 -DiskGB 1
} else {
	Write-Host "VM1 already exists"
}
Get-VM