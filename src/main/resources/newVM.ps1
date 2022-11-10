<# Variable #>
$Name = $args[0]
$OS = $args[1]
$CPU = $args[2]
$RAM = $args[3]
$Storage = $args[4]
$OvfPath

If ($OS -eq "UBUNTU") {
	$OvfPath = "src/main/resources/ubuntu/UbuntuServer.ovf"
} elseif ($OS -eq "WINDOW") {
	$OvfPath = "src/main/resources/window/WindowServer.ovf"
}
Connect-VIServer "192.168.0.112" -User root -Password Trung@65730208 -SaveCredentials | Out-Null
$existVM = Get-VM -Name $Name -ErrorAction SilentlyContinue
If (!$existVM) {
	Import-VApp -Name $Name -Source $OvfPath -VMHost 192.168.0.112 -Datastore datastore1 -DiskStorageFormat Thin | Out-Null
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

