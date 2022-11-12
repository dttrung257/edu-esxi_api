<# Variable #>
$IpServer = $args[0]
$ServerUsername = $args[1]
$Password = $args[2]
$Name = $args[3]
$OS = $args[4]
$CPU = $args[5]
$RAM = $args[6]
$Storage = $args[7]
$OvfPath

If ($OS -eq "UBUNTU") {
	$OvfPath = "src/main/resources/ubuntu/UbuntuServer.ovf"
} elseif ($OS -eq "WINDOW") {
	$OvfPath = "src/main/resources/window/WindowServer.ovf"
}
Connect-VIServer "$IpServer" -User $ServerUsername -Password $Password -SaveCredentials | Out-Null
$existVM = Get-VM -Name $Name -ErrorAction SilentlyContinue
If (!$existVM) {
	Import-VApp -Name $Name -Source $OvfPath -VMHost $IpServer -Datastore datastore1 -DiskStorageFormat Thin | Out-Null
	#Set CPU RAM Storage
	Set-VM -VM $Name -NumCpu $CPU -MemoryGB $RAM -Confirm:$false | Out-Null

	Get-HardDisk -VM $Name | Set-HardDisk -CapacityGB $Storage -Confirm:$false  | Out-Null

	<# Start VM #>
	Start-VM -VM $Name | Out-Null

	<# Get ip address #> 
	$found = 0

	while ($found -ne 1) {
		Start-Sleep -Seconds 5
		$ip = (Get-VM -Name $Name).Guest.IPAddress[0]
		if ("$ip" -match '(\b25[0-5]|\b2[0-4][0-9]|\b[01]?[0-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}') {
			$found = 1
			Write-Host $ip
		}
	}
}

