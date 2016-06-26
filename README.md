# RankSQL

RankSQL is a plugin made for one special case:

- You have a set up a network with some servers with completely different permissions. (E.g. SkyWars and Prison)
- Some players need to have the same rank on each server. (E.g. VIP, Moderator, Admin)
- The permissions of these ranks may variate on the servers.

RankSQL synchronises these special ranks via MySQL and applies them to the local permission system via Vault so you
can define these ranks on a local base but don't have to apply these ranks for each player on each server.

**Requirements:**
- Vault
- NTApi
