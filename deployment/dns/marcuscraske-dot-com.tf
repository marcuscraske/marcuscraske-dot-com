resource "cloudflare_zone" "marcuscraske-dot-com" {
    zone = "marcuscraske.com"
}

########################################################################################################################
# Main website
########################################################################################################################

resource "cloudflare_record" "marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = data.terraform_remote_state.main-website.outputs.endpoint
    type        = "CNAME"
    proxied     = true
    ttl         = 1
}

resource "cloudflare_record" "www-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "www"
    value       = "marcuscraske.com"
    type        = "CNAME"
    proxied     = true
}

resource "cloudflare_page_rule" "https" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    target      = "*.marcuscraske.com/*"
    actions {
        always_use_https = true
    }
}


########################################################################################################################
# E-mail
########################################################################################################################

resource "cloudflare_record" "txt-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "google-site-verification=DBcgfpteK4PkrKkQ4QxIW-IzOoM5qfhV5rM_4iSRKlg"
    type        = "TXT"
}

resource "cloudflare_record" "alt4-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt4.aspmx.l.google.com"
    type        = "MX"
    priority    = "10"
}

resource "cloudflare_record" "alt3-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt3.aspmx.l.google.com"
    type        = "MX"
    priority    = "10"
}

resource "cloudflare_record" "alt2-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt2.aspmx.l.google.com"
    type        = "MX"
    priority    = "5"
}

resource "cloudflare_record" "alt1-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt1.aspmx.l.google.com"
    type        = "MX"
    priority    = "5"
}

resource "cloudflare_record" "aspmx-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "aspmx.l.google.com"
    type        = "MX"
    priority    = "1"
}
