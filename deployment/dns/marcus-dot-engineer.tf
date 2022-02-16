resource "cloudflare_zone" "marcus-dot-engineer" {
    zone = "marcus.engineer"
}

module "marcus-dot-engineer-redirect" {
    source = "../dns-redirect"
    cloudflare_zone_id = cloudflare_zone.marcus-dot-engineer.id
    cloudflare_hostname = cloudflare_zone.marcus-dot-engineer.zone
}
